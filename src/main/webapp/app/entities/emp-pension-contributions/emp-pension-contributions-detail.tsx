import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emp-pension-contributions.reducer';
import { IEmpPensionContributions } from 'app/shared/model/emp-pension-contributions.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmpPensionContributionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmpPensionContributionsDetail extends React.Component<IEmpPensionContributionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { empPensionContributionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.empPensionContributions.detail.title">EmpPensionContributions</Translate> [
            <b>{empPensionContributionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="amountPaid">
                <Translate contentKey="accounTalkApp.empPensionContributions.amountPaid">Amount Paid</Translate>
              </span>
            </dt>
            <dd>{empPensionContributionsEntity.amountPaid}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.empPensionContributions.employmentDetails">Employment Details</Translate>
            </dt>
            <dd>{empPensionContributionsEntity.employmentDetails ? empPensionContributionsEntity.employmentDetails.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/emp-pension-contributions" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/emp-pension-contributions/${empPensionContributionsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ empPensionContributions }: IRootState) => ({
  empPensionContributionsEntity: empPensionContributions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmpPensionContributionsDetail);
