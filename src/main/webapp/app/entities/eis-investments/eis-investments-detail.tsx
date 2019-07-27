import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './eis-investments.reducer';
import { IEisInvestments } from 'app/shared/model/eis-investments.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEisInvestmentsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EisInvestmentsDetail extends React.Component<IEisInvestmentsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { eisInvestmentsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.eisInvestments.detail.title">EisInvestments</Translate> [<b>{eisInvestmentsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="investmentScheme">
                <Translate contentKey="accounTalkApp.eisInvestments.investmentScheme">Investment Scheme</Translate>
              </span>
            </dt>
            <dd>{eisInvestmentsEntity.investmentScheme}</dd>
            <dt>
              <span id="dateInvested">
                <Translate contentKey="accounTalkApp.eisInvestments.dateInvested">Date Invested</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={eisInvestmentsEntity.dateInvested} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="amountPaid">
                <Translate contentKey="accounTalkApp.eisInvestments.amountPaid">Amount Paid</Translate>
              </span>
            </dt>
            <dd>{eisInvestmentsEntity.amountPaid}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.eisInvestments.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{eisInvestmentsEntity.taxReturn ? eisInvestmentsEntity.taxReturn.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/eis-investments" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/eis-investments/${eisInvestmentsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ eisInvestments }: IRootState) => ({
  eisInvestmentsEntity: eisInvestments.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EisInvestmentsDetail);
