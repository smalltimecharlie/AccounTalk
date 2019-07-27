import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './per-pension-contributions.reducer';
import { IPerPensionContributions } from 'app/shared/model/per-pension-contributions.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPerPensionContributionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PerPensionContributionsDetail extends React.Component<IPerPensionContributionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { perPensionContributionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.perPensionContributions.detail.title">PerPensionContributions</Translate> [
            <b>{perPensionContributionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="netAmountPaid">
                <Translate contentKey="accounTalkApp.perPensionContributions.netAmountPaid">Net Amount Paid</Translate>
              </span>
            </dt>
            <dd>{perPensionContributionsEntity.netAmountPaid}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.perPensionContributions.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{perPensionContributionsEntity.taxReturn ? perPensionContributionsEntity.taxReturn.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.perPensionContributions.pensionProvider">Pension Provider</Translate>
            </dt>
            <dd>{perPensionContributionsEntity.pensionProvider ? perPensionContributionsEntity.pensionProvider.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/per-pension-contributions" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/per-pension-contributions/${perPensionContributionsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ perPensionContributions }: IRootState) => ({
  perPensionContributionsEntity: perPensionContributions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PerPensionContributionsDetail);
