import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tax-return.reducer';
import { ITaxReturn } from 'app/shared/model/tax-return.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITaxReturnDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TaxReturnDetail extends React.Component<ITaxReturnDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { taxReturnEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.taxReturn.detail.title">TaxReturn</Translate> [<b>{taxReturnEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="year">
                <Translate contentKey="accounTalkApp.taxReturn.year">Year</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={taxReturnEntity.year} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="studentLoan">
                <Translate contentKey="accounTalkApp.taxReturn.studentLoan">Student Loan</Translate>
              </span>
            </dt>
            <dd>{taxReturnEntity.studentLoan}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.taxReturn.bankRefundDetails">Bank Refund Details</Translate>
            </dt>
            <dd>{taxReturnEntity.bankRefundDetails ? taxReturnEntity.bankRefundDetails.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.taxReturn.statePensionDetails">State Pension Details</Translate>
            </dt>
            <dd>{taxReturnEntity.statePensionDetails ? taxReturnEntity.statePensionDetails.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.taxReturn.client">Client</Translate>
            </dt>
            <dd>{taxReturnEntity.client ? taxReturnEntity.client.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/tax-return" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/tax-return/${taxReturnEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ taxReturn }: IRootState) => ({
  taxReturnEntity: taxReturn.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TaxReturnDetail);
